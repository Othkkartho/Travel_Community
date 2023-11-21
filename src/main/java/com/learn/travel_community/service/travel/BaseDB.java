package com.learn.travel_community.service.travel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.*;

@Slf4j
public class BaseDB {

    public List<Map<String, Object>> baseDB (String sql) {
//        log.info("*********** {} ***********", "BaseDB baseDB Start");
        try {
//            log.info("*********** sql : {} ***********", sql);
            List<Map<String, Object>> resultList = new ArrayList<>();

            String url = "jdbc:mysql://localhost:3307/travel?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
            String user = "traveler";
            String passwd = "Baejjae8218@";
            Connection con = DriverManager.getConnection(url, user, passwd);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map<String, Object> resultMap = new HashMap<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    resultMap.put(rsmd.getColumnName(i), rs.getString(i));
                }
                resultList.add(resultMap);
                // log.info("*********** resultMap : {} ***********", resultMap);
            }

//            log.info("*********** resultList : {} ***********", resultList);

            rs.close();
            stmt.close();
            con.close();
            return resultList;
        } catch (SQLException e) {
            log.info("*********** {} ***********", "DB connect fail, SQL error");
            log.info("*********** {} ***********", "reason : " + e.getMessage());
            return null;
        }
    }
}