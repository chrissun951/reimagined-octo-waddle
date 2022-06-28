package com.example.demoyqb.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = YqbProperties.PREFIX)
//@Configuration
@Component
public class YqbProperties {
    public static final String PREFIX = "yqb.config";

    private String url;
    private String sftpAddress;
    private String sftpPort;
    private String sftpUserName;
    private String sftpPassword;

    private  String signMerchantNo;
    private  String merchantKey;
    private  String merchantPriKey;
    private  String merchantPubKey;
    private  String publicKey;


}
