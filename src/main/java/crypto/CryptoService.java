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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
public final class CryptoService {

    static final String EC_CURVE = "secp256r1";
    private static final String KEYSTORE_FORMAT = "UBER";
    private static final String ROOTNAME = "CN=apex-network.com";
    private static final String KEYSTORE_FILE_FORMAT = ".ubr";
    private static final String ALGORITHM = "ECDSA";
    private static final String PROVIDER = "BC";
    private static final String SIGNER_ALGORITHM = "SHA256withECDSA";

    private static final MessageDigest ripeMd160 = new RIPEMD160.Digest();
    private static final MessageDigest sha256 = new SHA256.Digest();
    private static final MessageDigest keccak256 = new Keccak.Digest256();

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void generateKeystore(String keyStoreName, String password, String keyName) throws Exception {
        final ECNamedCurveParameterSpec ecGenSpec = ECNamedCurveTable.getParameterSpec(EC_CURVE);
        KeyPairGenerator g = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        g.initialize(ecGenSpec, new SecureRandom());
        final KeyPair keyPair = g.generateKeyPair();
        keyPairToKeyStore(keyStoreName, password, keyName, keyPair);
    }

    public void keyPairToKeyStore(String keyStoreName, String password, String keyName, KeyPair keyPair) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FORMAT);
        keyStore.load(null, password.toCharArray());
        X509Certificate[] certificateChain = new X509Certificate[1];
        certificateChain[0] = generateCertificate(keyPair);
        keyStore.setKeyEntry(keyName, keyPair.getPrivate(), password.toCharArray(), certificateChain);
        try (FileOutputStream fos = new FileOutputStream(keyStoreName + KEYSTORE_FILE_FORMAT)) {
            keyStore.store(fos, password.toCharArray());
        }
    }

    public void generateKeyStoreFromRawBytes(String keyStoreName, String password, String keyName, String raw) throws Exception {
        final ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(EC_CURVE);
        final ECNamedCurveSpec params = new ECNamedCurveSpec(EC_CURVE, ecSpec.getCurve(), ecSpec.getG(), ecSpec.getN());
        final ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(new BigInteger(raw, 16), params);
        final KeyFactory kf = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        final ECPrivateKey privateKey = (ECPrivateKey) kf.generatePrivate(ecPrivateKeySpec);
        final BCECPrivateKey bcec = (BCECPrivateKey) privateKey;
        final ECPoint Q = ecSpec.getG().multiply(bcec.getD());
        final byte[] publicBytes = Q.getEncoded(false);
        final ECPoint point = ecSpec.getCurve().decodePoint(publicBytes);
        final ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);
        final ECPublicKey publicKey = (ECPublicKey) kf.generatePublic(pubSpec);
        final KeyPair keyPair = new KeyPair(publicKey, privateKey);
        keyPairToKeyStore(keyStoreName, password, keyName, keyPair);
    }

    public KeyPair loadKeyPairFromKeyStore(String filename, String password, String keyName) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FORMAT);
        try(InputStream in = new FileInputStream(filename)){
            keyStore.load(in, password.toCharArray());
        }
        final PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyName, password.toCharArray());
        final X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyName);
        final PublicKey publicKey = certificate.getPublicKey();
        return new KeyPair(publicKey, privateKey);
    }

    public byte[] getSignature(PrivateKey privateKey, byte[] data) throws Exception {
        Signature signature = Signature.getInstance(SIGNER_ALGORITHM, PROVIDER);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public boolean verifySignature(PublicKey publicKey, byte[] data, byte[] signatureBytes) throws Exception {
        Signature signature = Signature.getInstance(SIGNER_ALGORITHM, PROVIDER);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signatureBytes);
    }

    public PublicKey encodedToPublicKey(byte[] bytes) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        return factory.generatePublic(x509EncodedKeySpec);
    }

    private X509Certificate generateCertificate(KeyPair keyPair) throws Exception {
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

    public static byte[] getRIPEMD160(byte [] bytes) {
        return ripeMd160.digest(sha256.digest(bytes));
    }

    public static byte [] getRIPEMD160(String str) {
        return ripeMd160.digest(sha256.digest(str.getBytes()));
    }

    public static byte[] getSHA256(byte [] bytes) {
        return sha256.digest(sha256.digest(bytes));
    }

    public static byte[] getSHA256(String str) {
        return sha256.digest(sha256.digest(str.getBytes()));
    }

    public static byte[] getKeccak(byte [] bytes){
        return keccak256.digest(sha256.digest(bytes));
    }

    public static byte[] getKeccak(String str){
        return keccak256.digest(sha256.digest(str.getBytes()));
    }

}
