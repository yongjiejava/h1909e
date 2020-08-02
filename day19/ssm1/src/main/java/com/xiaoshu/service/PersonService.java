package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonExample;
import com.xiaoshu.entity.PersonExample.Criteria;
import com.xiaoshu.entity.PersonVo;

@Service
public class PersonService {

	

	@Autowired
	private PersonMapper personMapper;
	public PageInfo<PersonVo> findUserPage(PersonVo person, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> list = personMapper.findPage(person);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(list);
		return pageInfo;
	}
	// 通过用户名判断是否存在，（新增时不能重名）
	public Person existUserWithUserName(String userName) throws Exception {
		PersonExample example = new PersonExample();
		Criteria criteria = example.createCriteria();
		criteria.andPnameEqualTo(userName);
		List<Person> userList = personMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};
	// 新增
	public void addPerson(Person t) throws Exception {
		personMapper.insert(t);
	};

	// 修改
	public void updatePerson(Person t) throws Exception {
		personMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deletePerson(Integer id) throws Exception {
		personMapper.deleteByPrimaryKey(id);
	};
}
