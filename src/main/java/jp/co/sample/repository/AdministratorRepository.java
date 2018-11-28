package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリクラス.
 * 
 * @author rks
 *
 */
@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Administrator> administratorRowMapper = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setID(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	/**
	 * 管理者を登録するメソッド.
	 * 
	 * @param administrator
	 *            管理者情報
	 */
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mailaddress, :password);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", administrator.getID())
				.addValue("name", administrator.getName()).addValue("mailaddress", administrator.getMailAddress())
				.addValue("password", administrator.getPassword());
		template.update(sql, param);
	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得するメソッド
	 * 
	 * @param mailAddress
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 * @return 取得した管理者情報
	 */
	public Administrator findByMailAddressAndPassward(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		Administrator administrator;
		try {
		administrator= template.queryForObject(sql, param, administratorRowMapper);
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			System.err.println(e + "ログイン処理に失敗しました");
			administrator = null;
		}
		return administrator;
	}

}
