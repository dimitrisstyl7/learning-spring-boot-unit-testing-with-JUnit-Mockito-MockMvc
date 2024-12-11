package dimstyl.introtomockito.service;

import dimstyl.introtomockito.dao.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    public double calculateGradesSum(List<Double> grades) {
        return applicationDao.calculateGradesSum(grades);
    }

    public double findGradePointAverage(List<Double> grades) {
        return applicationDao.findGradePointAverage(grades);
    }

    public Object checkNull(Object obj) {
        return applicationDao.checkNull(obj);
    }

}
