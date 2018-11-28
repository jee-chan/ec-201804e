package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者画面の操作を行うコントローラクラス.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorContoroller {

	@Autowired
	private HttpSession session;

	@Autowired
	private AdministratorService administratorService;

	/**
	 * 送信されてきた値をmodelオブジェクトに格納するメソッド
	 * 
	 * @return スコープに格納するフォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		System.out.println("フォームの送信");
		InsertAdministratorForm insertAdministratorForm = new InsertAdministratorForm();

		return insertAdministratorForm;
	}

	/**
	 * 送信されてきた値をmodelオブジェクトに格納するメソッド.
	 * 
	 * @return スコープに格納するフォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}

	/**
	 * ログイン画面に遷移させるメソッド.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login.html";
	}

	/**
	 * 登録画面に遷移させるメソッド.
	 * 
	 * @return 登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert.html";
	}

	/**
	 * 管理者を登録するメソッド
	 * 
	 * @param form
	 *            リクエストパラメータから送信された情報
	 * @return メインページにリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator(null, form.getName(), form.getMailAddress(),
				form.getPassword());
		administratorService.insert(administrator);
		return "redirect:/";
	}

	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
			return toLogin();
		}
		session.setAttribute("administratorName", administrator.getName());
		return "forward:/employee/showList";
	}

	/**
	 * ログアウト処理(今回はセッションの削除のみ)
	 * 
	 * @param form
	 *            フォーム
	 * @param model
	 *            モデル
	 * @param session
	 *            セッション
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/logout")
	public String logout(UpdateEmployeeForm form, Model model) {
		session.invalidate();
		return "redirect:/";
	}
}
