/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 - 2019
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package crypto;

import message.transaction.ISerialize;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jcajce.provider.digest.RIPEMD160;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Hex;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;

/**
 * This class provides cryptographic methods
 * @author Artem Eger
 * @since 18.08.2019
 */
public class CryptoService {

    static final String EC_CURVE = "secp256r1";
    private static final String KEYSTORE_FORMAT = "UBER";
    private static final String ROOTNAME = "CN=apex-network.com";
    private static final String KEYSTORE_FILE_FORMAT = ".ubr";
    private static final String ALGORITHM = "ECDSA";
    private static final String PROVIDER = "BC";
    private static final String SIGNER_ALGORITHM = "SHA256withECDSA";

    public static final String KEY_NAME = "cpxkey";
    public static final MessageDigest ripeMd160 = new RIPEMD160.Digest();
    public static final MessageDigest sha256 = new SHA256.Digest();
    public static final MessageDigest keccak256 = new Keccak.Digest256();

    private final ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(EC_CURVE);
    private final ECNamedCurveSpec params = new ECNamedCurveSpec(EC_CURVE, ecSpec.getCurve(), ecSpec.getG(), ecSpec.getN());

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public KeyStore generateKeystore(final String password) throws Exception {
        final ECNamedCurveParameterSpec ecGenSpec = ECNamedCurveTable.getParameterSpec(EC_CURVE);
        final KeyPairGenerator g = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        g.initialize(ecGenSpec, new SecureRandom());
        final KeyPair keyPair = g.generateKeyPair();
        return keyPairToKeyStore(password, KEY_NAME, keyPair);
    }

    public KeyStore keyPairToKeyStore(final String password, final String keyName, final KeyPair keyPair) throws Exception {
        final KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FORMAT);
        keyStore.load(null, password.toCharArray());
        final X509Certificate[] certificateChain = new X509Certificate[1];
        certificateChain[0] = generateCertificate(keyPair);
        keyStore.setKeyEntry(keyName, keyPair.getPrivate(), password.toCharArray(), certificateChain);
        return keyStore;
    }

    public KeyStore generateKeyStoreFromRawString(final String password, final String keyName, final String raw) throws Exception {
        final ECPrivateKey privateKey = getECPrivateKeyFromRawString(raw);
        final BCECPrivateKey bcec = (BCECPrivateKey) privateKey;
        final KeyFactory kf = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        final ECPoint Q = ecSpec.getG().multiply(bcec.getD());
        final byte[] publicBytes = Q.getEncoded(false);
        final ECPoint point = ecSpec.getCurve().decodePoint(publicBytes);
        final ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);
        final ECPublicKey publicKey = (ECPublicKey) kf.generatePublic(pubSpec);
        final KeyPair keyPair = new KeyPair(publicKey, privateKey);
        return keyPairToKeyStore(password, keyName, keyPair);
    }

    public KeyStore generateKeyStoreFromMnemonic(final String password, final String mnemonic) throws Exception {
        final String rawKey = Hex.toHexString(MnemonicUtils.generateEntropy(mnemonic));
        return generateKeyStoreFromRawString(password, KEY_NAME, rawKey);
    }

    public KeyStore generateKeyStoreFromWif(final String password, final String wif) throws Exception {
        final String rawKey = CPXKey.getRawFromWIF(wif);
        return generateKeyStoreFromRawString(password, KEY_NAME, rawKey);
    }

    public ECPrivateKey getECPrivateKeyFromRawString(final String raw) throws Exception {
        final ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(new BigInteger(raw, 16), params);
        final KeyFactory kf = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        return (ECPrivateKey) kf.generatePrivate(ecPrivateKeySpec);
    }

    public KeyPair loadKeyPairFromKeyStore(final byte [] keystoreBytes, final String password, final String keyName) throws Exception {
        final KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FORMAT);
        try(InputStream in = new ByteArrayInputStream(keystoreBytes)){
            keyStore.load(in, password.toCharArray());
        }
        final PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyName, password.toCharArray());
        final X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyName);
        final PublicKey publicKey = certificate.getPublicKey();
        return new KeyPair(publicKey, privateKey);
    }

    public byte[] getSignature(final PrivateKey privateKey, final byte[] data) throws Exception {
        final Signature signature = Signature.getInstance(SIGNER_ALGORITHM, PROVIDER);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public boolean verifySignature(final PublicKey publicKey, final byte[] data, final byte[] signatureBytes) throws Exception {
        final Signature signature = Signature.getInstance(SIGNER_ALGORITHM, PROVIDER);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signatureBytes);
    }

    public byte[] signBytes(final PrivateKey privateKey, final ISerialize objToSign) throws Exception {
        final Signature signature = Signature.getInstance(SIGNER_ALGORITHM, PROVIDER);
        signature.initSign(privateKey);
        signature.update(objToSign.getBytes());
        final byte[] signatureBytes = signature.sign();
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()) {
            try (DataOutputStream obj = new DataOutputStream(out)) {
                obj.write(objToSign.getBytes());
                obj.write(signatureBytes.length);
                obj.write(signatureBytes);
                return out.toByteArray();
            }
        }
    }

    public PublicKey encodedToPublicKey(final byte[] bytes) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        return factory.generatePublic(x509EncodedKeySpec);
    }

    private X509Certificate generateCertificate(final KeyPair keyPair) throws Exception {
        Calendar calendar = Calendar.getInstance();
        Date validFrom = calendar.getTime();
        calendar.add(Calendar.YEAR, 1000);
        Date validUntil = calendar.getTime();
        X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(
                new X500Name(ROOTNAME),
                new BigInteger(64, new SecureRandom()),
                validFrom, validUntil,
                new X500Name(ROOTNAME),
                keyPair.getPublic());
        ContentSigner signer = new JcaContentSignerBuilder(SIGNER_ALGORITHM).build(keyPair.getPrivate());
        X509CertificateHolder certHolder = builder.build(signer);
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(PROVIDER).getCertificate(certHolder);
        cert.verify(keyPair.getPublic());
        return cert;
    }

    public static byte[] getRIPEMD160(final byte [] bytes) {
        return ripeMd160.digest(sha256.digest(bytes));
    }

    public static byte[] getSHA256(final byte [] bytes) {
        return sha256.digest(sha256.digest(bytes));
    }
}
