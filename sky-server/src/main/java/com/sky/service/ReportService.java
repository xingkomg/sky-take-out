package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {

    TurnoverReportVO getTurnoverReportVO(LocalDate begin,LocalDate end);

    UserReportVO getUserReportVO(LocalDate begin, LocalDate end);

    OrderReportVO getOrdersReportVO(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getTop10ReportVO(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response);
}
