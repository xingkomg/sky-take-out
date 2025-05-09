package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {

    TurnoverReportVO getTurnoverReportVO(LocalDate begin,LocalDate end);
}
