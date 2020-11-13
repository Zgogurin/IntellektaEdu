package ru.education.exceptions;

//Исключение выбрасывается при вызове метода сервиса с неккоректными параметрами
public class EntityIllegalArgumentException extends BaseException {


    public EntityIllegalArgumentException(String message) {
        super(message);
    }
}
