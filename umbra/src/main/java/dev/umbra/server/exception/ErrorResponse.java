package dev.umbra.server.exception;

public record ErrorResponse (int status, String message) { }