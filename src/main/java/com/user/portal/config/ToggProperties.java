package com.user.portal.config;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "togg")
public class ToggProperties {
    public Security security;
    public Mail mail;

    public static class Security {
        public Authentication authentication;

        public static class Authentication {
            public Jwt jwt;

            public static class Jwt {
                public String issuer;
                public long tokenValidityInSeconds;
                public long tokenValidityInSecondsForRememberMe;
                public PrivateKey privateKey;

                public static class PrivateKey {
                    public String location;
                }
            }
        }
    }

    public static class Mail {
        public String baseUrl;
    }
}
