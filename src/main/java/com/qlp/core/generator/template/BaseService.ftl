package ${target.rootPath}.service.${target.parentPath};

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${target.rootPath}.dao.${target.parentPath}.${target.name}Dao;
import ${target.rootPath}.entity.${target.parentPath}.${target.name};

import com.qlp.core.page.Page;
import com.qlp.core.page.PageImpl;
import com.qlp.core.page.Pageable;
import com.qlp.core.util.LogUtil;

@Service("${target.lowName}Service")
@Transactional(readOnly = true)
public class ${target.name}Service {
	
	private static final Logger logger = LoggerFactory.getLogger(${target.name}Service.class);

	@Autowired
	private ${target.name}Dao ${target.lowName}Dao; 
	
	public Page<${target.name}> queryPage(Pageable<${target.name}> pageable) {
		List<${target.name}> list = ${target.lowName}Dao.queryPageList(pageable);
		Page<${target.name}> page = new PageImpl<>(list, pageable);
		return page;
	}
	
	public ${target.name} queryById(Long id){
		${target.name} ${target.lowName} = ${target.lowName}Dao.queryById(id);
		return ${target.lowName};
	}

	public ${target.name} newIfNotFound(Long id) {
		if(id == null){
			return new ${target.name}();
		}
		return queryById(id);
	}

	@Transactional(readOnly = false)
	public void save(${target.name} ${target.lowName}) {
		${target.lowName}Dao.save${target.name}(${target.lowName});
	}

	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		${target.lowName}Dao.deleteById(id);
	}

}
