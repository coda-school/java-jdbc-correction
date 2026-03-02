package school.coda.tp.jdbc.shared;

public class TechnicalException extends RuntimeException {
    public TechnicalException(TechnicalException.ErrorCode errorCode, Throwable cause) {
        super(errorCode.code, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }

    public enum ErrorCode {
        DATABASE_ERROR("database.error"),
        UNABLE_TO_CLOSE_DATABASE_RESOURCE("database.unable.to.close.resource");

        public final String code;

        ErrorCode(String code) {
            this.code = code;
        }
    }
}
