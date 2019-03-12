package cyx.sell.exception;

/**
 * 使用场景：一般在调用银行接口的时候，银行都会要求，如果返回的json有为空的字段或者有异常，返回的httpStatus!=200
 * 假设抛出ResponseBankException时就要求返回的httpStatus!=200
 */
public class ResponseBankException extends RuntimeException{
}
