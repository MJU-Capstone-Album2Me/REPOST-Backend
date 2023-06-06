package com.album2me.repost.domain.notification.model;

public enum NotificationCommand {

    APPLY("님께서 가입 요청을 보냈습니다"),
    ENTRANCE("에 가입이 완료되셨습니다."),
    COMMENT("님께서 댓글을 남겼습니다."),
    REPLY("님께서 답글을 남겼습니다.");

    private final String message;

    NotificationCommand(String message) {
        this.message = message;
    }

    public String createMessage(String name) {
        return name + message;
    }
}
