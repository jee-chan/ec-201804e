package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員画面の操作を行うコントローラクラス.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 送信されてきた従業員更新情報をリクエストスコープに格納する.
	 * @return 取得した情報
	 */
	@ModelAttribute
	private UpdateEmployeeForm setUpUpdateEmployeeForm() {
		UpdateEmployeeForm form = new UpdateEmployeeForm();

		return form;
	}

	/**
	 * 従業員リストを表示する.
	 * @param model モデル
	 * @return 従業員リスト
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 従業員詳細を表示する.
	 * @param id id
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail((Integer.parseInt(id)));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	/**
	 * 従業員情報を更新する
	 * @param form 入力されたフォーム情報
	 * @param model モデル
	 * @return 従業員リスト画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form, Model model) {
		Employee employee = new Employee();
		
		employee.setID(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:showList";
	}
}
