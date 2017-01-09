package ${target.rootPath}.controller.${target.parentPath};

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ${target.rootPath}.entity.${target.parentPath}.${target.name};
import ${target.rootPath}.service.${target.parentPath}.${target.name}Service;

import com.qlp.core.annotation.PageRequestParam;
import com.qlp.core.page.Page;
import com.qlp.core.page.Pageable;

@Controller
@RequestMapping(value = "/${target.lowName}")
public class ${target.name}Controller {
	
	@Autowired
	private ${target.name}Service ${target.lowName}Service;
	
	@SuppressWarnings({"unchecked" })
	@PageRequestParam
	@RequestMapping("/list")
	public String list(HttpServletRequest request,@ModelAttribute ${target.name} ${target.lowName}){
		Pageable<${target.name}> pageable =  (Pageable<${target.name}>) request.getAttribute("pageable");
		Page<${target.name}> pageInfo = ${target.lowName}Service.queryPage(pageable);
		request.setAttribute("${target.lowName}", ${target.lowName});
		request.setAttribute("pageInfo", pageInfo);
		return "/${target.appName}/${target.lowName}/list";
	}
	
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,@RequestParam(value = "id", required = false) Long id){
		${target.name} ${target.lowName} = ${target.lowName}Service.newIfNotFound(id);
		request.setAttribute("${target.lowName}", ${target.lowName});
		return "/${target.appName}/${target.name}/edit";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute ${target.name} ${target.lowName}){
		${target.lowName}Service.save(${target.lowName});
		return "redirect:list";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(HttpServletRequest request,@PathVariable("id") Long id){
		${target.lowName}Service.deleteById(id);
		return "redirect:list";
	}

}
