package bekyiu.utils;

import lombok.Getter;

public enum HttpStatus
{
    OK("200", "OK"),
    ERR("999", "Other Exceptions");

    @Getter
    private String code;
    @Getter
    private String msg;

    HttpStatus(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
}
