package us.drullk.memes;

public record Message(String message) {
    public String getMessage() {
        return this.message;
    }
}