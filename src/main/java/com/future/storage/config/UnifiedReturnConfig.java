package com.future.storage.config;

import com.future.storage.utils.AuthorizationException;
import com.future.storage.utils.BusinessException;
import com.future.storage.utils.CommonResult;
import com.future.storage.utils.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 通用返回配置类
 *
 * @author shiyong
 * 2019-10-08 11:18
 */
@EnableWebMvc
@Configuration
@Slf4j
public class UnifiedReturnConfig {

    /**
     * 统一返回数据格式
     * @author shiyong
     * 2019-10-08 17:18
     */
    @RestControllerAdvice("com.future.storage.controller")
    static class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                      MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                      ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

            if (body instanceof CommonResult) {
                return body;
            }

            return new CommonResult<>(body);
        }
    }

    /**
     * 统一异常处理
     * @author shiyong
     * 2019-10-08 17:19
     */
    @RestControllerAdvice("com.future.storage.controller")
    static class UnifiedExceptionHandler{

        /**
         * 处理业务异常
         * @author shiyong
         * 2019-10-08 17:19
         * @param e 异常
         * @return org.springframework.http.ResponseEntity<com.future.storage.utils.CommonResult>
         */
        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<CommonResult> handleBusinessException(BusinessException e) {

            CommonResult<Void> commonResult = new CommonResult<>(CommonResult.FAIL, e.getErrorMsg());

            return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /**
         * 处理身份异常
         * @author shiyong
         * 2019-10-09 10:24
         * @param e 异常
         * @return org.springframework.http.ResponseEntity<com.future.storage.utils.CommonResult>
         */
        @ExceptionHandler(AuthorizationException.class)
        public ResponseEntity<CommonResult> handleAuthorizationException(AuthorizationException e) {
            CommonResult<Void> commonResult = new CommonResult<>(CommonResult.FAIL, e.getErrorMsg());

            return new ResponseEntity<>(commonResult, HttpStatus.FORBIDDEN);
        }

        /**
         * 处理参数异常
         * @author shiyong
         * 2019-10-08 17:21
         * @return org.springframework.http.ResponseEntity<com.future.storage.utils.CommonResult>
         */
        @ExceptionHandler(ParameterException.class)
        public ResponseEntity<CommonResult> handleParameterException(ParameterException e) {
            CommonResult<Void> commonResult = new CommonResult<>(CommonResult.FAIL, e.getErrorMsg());

            return new ResponseEntity<>(commonResult, HttpStatus.BAD_REQUEST);
        }

        /**
         * 处理一般异常
         * @author shiyong
         * 2019-10-08 17:21
         * @return org.springframework.http.ResponseEntity<com.future.storage.utils.CommonResult>
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<CommonResult> handleException(Exception e) {

            CommonResult<Void> commonResult = new CommonResult<>(CommonResult.FAIL, "服务器异常！");

            log.error("服务器异常！", e);

            return new ResponseEntity<>(commonResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
