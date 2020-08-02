package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.dao.EmpMapper;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpExample;
import com.xiaoshu.entity.EmpExample.Criteria;
import com.xiaoshu.entity.EmpVo;
import com.xiaoshu.entity.User;

@Service
public class EmpService {

	/*@Autowired
	UserMapper userMapper;

	// 查询所有
	public List<User> findUser(User t) throws Exception {
		return userMapper.select(t);
	};

	// 数量
	public int countUser(User t) throws Exception {
		return userMapper.selectCount(t);
	};

	// 通过ID查询
	public User findOneUser(Integer id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	};

	

	// 登录
	public User loginUser(User user) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPasswordEqualTo(user.getPassword()).andUsernameEqualTo(user.getUsername());
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	// 通过角色判断是否存在
	public User existUserWithRoleId(Integer roleId) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}*/
	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private DeptMapper deptMapper;
	public PageInfo<EmpVo> findUserPage(EmpVo empVo, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		//查询员工列表
		List<EmpVo> elist = empMapper.findPage(empVo);
		PageInfo<EmpVo> pageInfo = new PageInfo<EmpVo>(elist);
		return pageInfo;
	}
	public List<EmpVo> findPage(EmpVo empVo) {
		//查询员工列表
		List<EmpVo> elist = empMapper.findPage(empVo);
		return elist;
	}
	
	//查询部门
	public List<Dept> findAllDept() {
		return deptMapper.selectAll();
	}
	//添加员工信息将其中一项当做消息内容发送
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueTextDestination;
	
	// 新增
	public void addEmp(final Emp t) throws Exception {
		/*发送员工名称
		 * jmsTemplate.send(queueTextDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(t.getEname());
			}
		});*/
		jmsTemplate.send(queueTextDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(t.getDepid()+"");
			}
		});
		empMapper.insert(t);
		
	};

	// 修改
	public void updateEmp(Emp t) throws Exception {
		empMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteEmp(Integer id) throws Exception {
		empMapper.deleteByPrimaryKey(id);
	};
	// 通过用户名判断是否存在，（新增时不能重名）
	public Emp existUserWithUserName(String userName) throws Exception {
		EmpExample example = new EmpExample();
		Criteria criteria = example.createCriteria();
		criteria.andEnameEqualTo(userName);
		List<Emp> userList = empMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}
	//查询报表
	public List<EmpVo> getEcharts() {
		return empMapper.getEcharts();
	};

}
