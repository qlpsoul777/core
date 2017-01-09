package ${target.rootPath}.dao.${target.parentPath};

import java.util.List;

import ${target.rootPath}.entity.${target.parentPath}.${target.name};

import com.qlp.core.page.Pageable;

public interface ${target.name}Dao {
	
	Long save${target.name}(${target.name} ${target.lowName});
	
	Integer deleteById(Long id);
	
	Integer update${target.name}(${target.name} ${target.lowName});
	
	List<${target.name}> queryPageList(Pageable<${target.name}> pageable);

	${target.name} queryById(Long id);
}
