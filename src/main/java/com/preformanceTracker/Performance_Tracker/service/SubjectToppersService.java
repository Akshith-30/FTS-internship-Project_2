package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.dto.TopStudentDTO;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class SubjectToppersService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<TopStudentDTO> getTop3StudentsPerSubjectPerYear() {
        List<Object[]> rawResults = gradeRepository.findAllSortedForTop3();

        Map<String, List<TopStudentDTO>> groupedMap = new LinkedHashMap<>();

        for (Object[] row : rawResults) {
            if (row.length < 4) continue;

            String subjectName = row[0] != null ? row[0].toString() : "Unknown Subject";
            String studentName = row[1] != null ? row[1].toString() : "Unknown Student";

            int marks = 0;
            int year = 0;

            // marks may come as Integer, BigInteger, BigDecimal or Long - handle safely
            Object marksObj = row[2];
            if (marksObj instanceof Number) {
                marks = ((Number) marksObj).intValue();
            } else {
                try {
                    marks = Integer.parseInt(marksObj.toString());
                } catch (Exception e) {
                    marks = 0;
                }
            }

            // year may come as Integer, BigInteger, Long etc.
            Object yearObj = row[3];
            if (yearObj instanceof Number) {
                year = ((Number) yearObj).intValue();
            } else {
                try {
                    year = Integer.parseInt(yearObj.toString());
                } catch (Exception e) {
                    year = 0;
                }
            }

            String key = subjectName + "_" + year;
            groupedMap.putIfAbsent(key, new ArrayList<>());

            if (groupedMap.get(key).size() < 3) {
                groupedMap.get(key).add(new TopStudentDTO(subjectName, studentName, marks, year));
            }
        }

        // Flatten grouped map to list
        return groupedMap.values().stream().flatMap(List::stream).toList();
    }
}
