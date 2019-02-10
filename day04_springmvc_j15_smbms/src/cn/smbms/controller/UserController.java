package cn.smbms.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.role.RoleServiceImpl;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name="userservice")
	private UserService userService;
	@Resource(name="roleService")
	private RoleService roleService;
	
	//服务器端校验
	@RequestMapping("/login.bdqn")
	public String login(@Valid User user,BindingResult br,HttpSession session,Model model){
		/*if(userCode!=null && !"".equals(userCode)){
			
		}*/
		if(br.hasErrors()){
			//返回提示页
			return "forward:/login.jsp";
		}
		//调用service方法，进行用户匹配
		//User user = userService.login(userCode,userPassword);
		//throw new RuntimeException("用户名或密码不正确");
		if(null != user){//登录成功
			//放入session
			session.setAttribute(Constants.USER_SESSION, user);
			//页面跳转（frame.jsp)
			return "frame";
		}else{
			//页面跳转（login.jsp）带出提示信息--转发
			model.addAttribute("error", "用户名或密码不正确");
			return "forward:/login.jsp";
		}
	}
	
	//跳转到添加页
	@RequestMapping("/goadd.bdqn")
	public String goaddpage(){
		//获得所有的角色列表-采用Ajax
		return "useradd";
	}
	
	//测试Spring mvc标签
	@RequestMapping("/testtag.bdqn")
	public String testtag(Model model){
		if(!model.containsAttribute("user")){
			
			User user=new User();
			user.setUserCode("admin");
			user.setUserPassword("123");
			user.setUserName("rose");
			
			model.addAttribute("user", user);
			
		}
		
		return "forward:/useradd_tag.jsp";
		
	}
	
	//执行添加-文件上传
	@RequestMapping("/doadd.bdqn")
	public String doadd(User user,HttpSession session,Model model,
	          @RequestParam(value ="attachs", required = false) MultipartFile[] attachs){
		//定义文件路径
		String idPicPath = null;
		String workPicPath = null;
		//提示
		String errorInfo = null;
		boolean flag = true;
		//获得文件上传路径
		String path = session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
		//循环读取上传文件
		for(int i = 0;i < attachs.length ;i++){
			MultipartFile attach = attachs[i];
			if(!attach.isEmpty()){
				if(i == 0){
					errorInfo = "uploadFileError";
				}else if(i == 1){
					errorInfo = "uploadWpError";
	        	}
				//原文件名
				String oldFileName = attach.getOriginalFilename();
				//原文件后缀 
				String prefix=FilenameUtils.getExtension(oldFileName);    
				/*
				以下处理可以配置
				int filesize = 500000;
				//上传大小不得超过 500k
		        if(attach.getSize() >  filesize){
		        	model.addAttribute(errorInfo, " * 上传大小不得超过 500k");
	            	flag = false;
	            }*/
		        //判断上传图片格式是否正确
		      /*  else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
	            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){*/
		        	//修改文件名
	            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal."+prefix;  
	                //创建文件
	            	File targetFile = new File(path, fileName);  
	            	//不存目录则创建目录
	                if(!targetFile.exists()){  
	                    targetFile.mkdirs();  
	                }  
	                try {  
	                	//保存文件
	                	attach.transferTo(targetFile);  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                    model.addAttribute(errorInfo, " * 上传失败！");
	                    flag = false;
	                }  
	                if(i == 0){
	                	 idPicPath =fileName;
	                }else if(i == 1){
	                	 workPicPath =fileName;
	                }
			}
		}
		//设置图片路径到对象
		user.setPersonidimg(idPicPath);
		user.setWorkcodeimg(workPicPath);
		
		//从页面参过来的数据都是String,String转基本基本数据类型，Spring MVC会自动转换。日期等对象的转换需要自定义转换
	
	    if(flag){
	    	if(userService.add(user)){
	    		return "forward:/user/query.bdqn";
	    	}else{
	    		return "useradd";
	    	}
	    }else{
			return "/user/query.bdqn";
		}
	    
	}
		
	//获得所有的角色列表-ajax
	@RequestMapping("/getUserRole.bdqn")
	// @ResponseBody 把集合->json字符串
	public @ResponseBody List<Role> getRoleList(){
		return roleService.getRoleList();
	}

	/*
	 * 判断账号是否已存在-ajax
	 * produces="application/json;charset=utf-8"-表示前端传递的参数是json对象格式
	 */
	@RequestMapping(value="/ucexits.bdqn",produces="application/json;charset=utf-8")
	public @ResponseBody HashMap<String, String>  ucexits(String userCode){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(userCode)){
			//userCode == null || userCode.equals("")
			resultMap.put("userCode", "exist");
		}else{
			User user = userService.selectUserCodeExist(userCode);
			if(null != user){
				resultMap.put("userCode","exist");
			}else{
				resultMap.put("userCode", "notexist");
			}
		}
		return resultMap;
	}
	
	//查询用户列表
	@RequestMapping("/query.bdqn")
	public String query(@RequestParam(value="queryname",required=false) String queryUserName,@RequestParam(value="queryUserRole",required=false) String  temp,@RequestParam(value="pageIndex",required=false) String pageIndex,HttpServletRequest request){
		//查询用户列表
		int queryUserRole = 0;
		List<User> userList = null;
		//设置页面容量
    	int pageSize = Constants.pageSize;
    	//当前页码
    	int currentPageNo = 1;
		if(queryUserName == null){
			queryUserName = "";
		}
		if(temp != null && !temp.equals("")){
			queryUserRole = Integer.parseInt(temp);
		}
		
    	if(pageIndex != null){
    		try{
    			currentPageNo = Integer.valueOf(pageIndex);
    		}catch(NumberFormatException e){
    			throw e;
    		}
    	}	
    	//总数量（表）	
    	int totalCount	= userService.getUserCount(queryUserName,queryUserRole);
    	//总页数
    	PageSupport pages=new PageSupport();
    	pages.setCurrentPageNo(currentPageNo);
    	pages.setPageSize(pageSize);
    	pages.setTotalCount(totalCount);
    	
    	int totalPageCount = pages.getTotalPageCount();
    	
    	//控制首页和尾页
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}
		
		
		userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo, pageSize);
		
		List<Role> roleList = null;
		RoleService roleService = new RoleServiceImpl();
		roleList = roleService.getRoleList();
		
		request.setAttribute("userList", userList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("queryUserName", queryUserName);
		request.setAttribute("queryUserRole", queryUserRole);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("currentPageNo", currentPageNo);
		//model.addAttribute("userList", userList);
		//request.getRequestDispatcher("userlist.jsp").forward(request, response);
		
		return "userlist";
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	//局部异常处理
	@ExceptionHandler(value={RuntimeException.class})
	public String handlerException(RuntimeException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}
	
	
}
