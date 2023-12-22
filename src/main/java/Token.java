/**
 * Token类
 */
public class Token {
    private TokenType tokenType;

    private String tokenValue;

    public Token(String tokenType, String tokenValue) {
        this.tokenType = TokenType.valueOf(tokenType);
        this.tokenValue = tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
