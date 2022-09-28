package com.spring.study.global.common.service;

import com.spring.study.global.common.response.CommonResult;
import com.spring.study.global.common.response.ListResult;
import com.spring.study.global.common.response.PageResult;
import com.spring.study.global.common.response.SingleResult;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ResponseService {

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    public <T> PageResult<T> getPageResult(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setPage(page);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(int code, String message) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(0);

        if (LocaleContextHolder.getLocale().equals(Locale.KOREA)
                || LocaleContextHolder.getLocale().equals(Locale.KOREAN)) {
            result.setMessage("성공하였습니다.");
        } else {
            result.setMessage("SUCCESS.");
        }

    }

}
