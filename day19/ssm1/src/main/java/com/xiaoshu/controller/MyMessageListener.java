package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.entity.Dept;

public class MyMessageListener implements MessageListener {

	@Autowired
	private DeptMapper deptMapper;
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String ename = textMessage.getText();
			int depid = Integer.parseInt(ename);
			Dept dept = deptMapper.selectByPrimaryKey(depid);
			System.out.println("接收到的员工部门信息为="+dept);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
