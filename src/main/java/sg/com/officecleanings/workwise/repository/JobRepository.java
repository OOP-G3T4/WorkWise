package sg.com.officecleanings.workwise.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sg.com.officecleanings.workwise.model.Job;

import java.util.List;

@Repository
public class JobRepository {
    private final JdbcTemplate jdbcTemplate;

    public JobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Job> fetchAllJobs() {
        String sql = "SELECT * FROM job WHERE status = 'Scheduled'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Job job = new Job();
            job.setJobId(rs.getInt("job_id"));
            job.setClientId(rs.getInt("client_id"));
            job.setPropertyId(rs.getInt("property_id"));
            job.setPackageId(rs.getString("package_id"));
            job.setDate(rs.getDate("date").toLocalDate());
            job.setStartTime(rs.getTime("start_time").toLocalTime());
            job.setActualDuration(rs.getInt("actual_duration"));
            return job;
        });
    }
}
