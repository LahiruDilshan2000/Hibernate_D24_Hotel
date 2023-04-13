package lk.ijse.gdse.d24_hotel.service.exception;

public class AllReadyBookedException extends RuntimeException{
    public AllReadyBookedException() {
    }

    public AllReadyBookedException(String message) {
        super(message);
    }

    public AllReadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AllReadyBookedException(Throwable cause) {
        super(cause);
    }

    public AllReadyBookedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
