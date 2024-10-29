package io.github.wittyprince.commons.cmm1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * CryptoConfigProperties
 *
 * @author WangChen
 * Created on 2024/10/29
 * @since 0.1
 */
@Component("cryptoConfigProperties")
@ConfigurationProperties(prefix = "crypto")
public class CryptoConfigProperties {

    @Value("${crypto.secret: e75e2d3126357d031ddaf412f9110f66}")
    private String secret;
    @Value("${crypto.iv: 1234567812345678}")
    private String iv;
    @Value("${crypto.algorithm: AES}")
    private String algorithm;
    @Value("${crypto.transformation: AES/CBC/PKCS5Padding}")
    private String transformation;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }
}
