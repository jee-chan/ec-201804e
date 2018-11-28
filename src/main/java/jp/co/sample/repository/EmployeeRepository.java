package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeeテーブルを操作するリポジトリクラス.
 * 
 * @author rks
 *
 */
@Repository
public class EmployeeRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Employee> employeeRowMapper = (rs, i) -> {
		Employee employee = new Employee();
		employee.setID(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	/**
	 * 従業員情報の全件検索を行うメソッド.
	 * 
	 * @return 従業員情報のリスト
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id, name, image, gender, hire_date,mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees ORDER BY hire_date";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Employee> employeeList = template.query(sql, param, employeeRowMapper);
		return employeeList;
	}

	/**
	 * 主キー情報から従業員情報を取得するメソッド.
	 * 
	 * @param id
	 *            id
	 * @return 取得した従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id, name, image, gender, hire_date,mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, employeeRowMapper);
		return employee;
	}

	/**
	 * 従業員情報を更新するメソッド
	 * 
	 * @param employee
	 *            従業員情報
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET dependents_count = :dependentsCount WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", employee.getID())
				.addValue("dependentsCount", employee.getDependentsCount());
//全件更新するSQL
		
		// String sql = "UPDATE employees SET name = :name, image = :image, gender =
		// :gender, hire_date = :hireDate, mail_address = :mailAddress, zip_code =
		// :zipCode, address = :address, telephone = :telephone, salary = :salary,
		// characteristics = :characteristics, dependents_count = :dependentsCount SET
		// WHERE id = :id";
		/**
		 * SqlParameterSource param = new MapSqlParameterSource().addValue("id",
		 * employee.getID()) .addValue("name", employee.getName()).addValue("gender",
		 * employee.getGender()) .addValue("hireDate",
		 * employee.getHireDate()).addValue("mailAddress", employee.getMailAddress())
		 * .addValue("zipCode", employee.getZipCode()).addValue("address",
		 * employee.getAddress()) .addValue("telephone",
		 * employee.getTelephone()).addValue("salary", employee.getSalary())
		 * .addValue("characteristics", employee.getCharacteristics())
		 * .addValue("dependentsCount", employee.getDependentsCount());
		 */
		template.update(sql, param);
	}
}
