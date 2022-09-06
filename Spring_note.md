# Spring框架 笔记 
## IOC 控制反转
- IOC（控制反转）是一种设计思想，DI（依赖注入）是实现IOC的一种的一种方法。
- 控制反转后将对象的创建转移给了第三方。
### IOC创建对象的方式
- 使用无参构造方法创建对象。
- 若要使用有参构造方法创建对象
	- 下标赋值：
		```xml
		<bean id="user1" class="com.kuang.pojo.User">  
		 <constructor-arg index="0" value="小红"/>  
		</bean>
		```
	- 类型赋值：
		```xml
		<bean id="user1" class="com.kuang.pojo.User">  
		 <constructor-arg type="java.lang.String" value="小红"/>  
		</bean>
		```
	- 参数名赋值：
		```xml
		<bean id="user1" class="com.kuang.pojo.User">  
		 <constructor-arg name="name" value="小红"/>  
		</bean>
		```
- 在配置文件加载时，容器中管理的对象就已经初始化了。
## Spring配置
### 别名
```xml
<!--
eg.
-->
<alias name="user" alias="userNew"/>
```
- 如果添加了别名，就可以用别名来获取这个对象。
### Bean的配置
- id：bean的唯一标识符，相当于对象名。
- class：bean对象所对应的全限定名（包名+类型）。
- name：也是别名，可以同时取多个。
```xml
<bean id="userT" class="com.kuang.pojo.UserT" name="user2 u2,u3;u4"/>
```
### import
- 一般用于团队开发，可以将多个配置文件，导入合并为一个。
```xml
<import resource="beans.xml"/>
```
 ## 依赖注入
 ### 构造器注入
 >见前“IOC创建对象的方式”。
 ### Set方式注入
 ```xml
<bean id="address" class="com.kuang.pojo.Address">  
	 <property name="address" value="xian"/>  
</bean> 
<bean id="student" class="com.kuang.pojo.Student">  
<!--        1.普通值注入 value-->
	  <property name="name" value="小明"/>  
	  
<!--        2.Bean注入 ref-->
	  <property name="address" ref="address"/>  
	  
<!-- 	数组注入-->  
	  <property name="books">  
		 <array> 
			 <value>红楼梦</value>  
			 <value>西游记</value>  
			 <value>水浒传</value>  
			 <value>三国演义</value>  
		 </array> 
 </property>
 
 <!--        List-->  
	  <property name="hobbies">  
		 <list> 
			 <value>听歌</value>  
			 <value>敲代码</value>  
			 <value>看电影</value>  
		 </list> 
		</property>

<!--        Map-->  
	  <property name="card">  
		 <map> 
			 <entry key="身份证" value="12131231231"/>  
			 <entry key="银行卡" value="43242343244"/>  
		 </map> 
		</property> 
<!--	Set-->
	<property name="games">  
		 <set> 
			 <value>LOL</value>  
			 <value>COC</value>  
			 <value>BOB</value>  
		 </set>
	 </property>

 <!--        null-->  
	 <property name="wife">  
		 <null/> 
	 </property>
 
 <!--        Properties-->  
	  <property name="info">  
		 <props> 
			 <prop key="driver">20190525</prop>  
			 <prop key="url">男</prop>  
			 <prop key="username">root</prop>  
			 <prop key="password">123456</prop>  
		 </props> 
		</property> 
 </bean>
 ```
 ### 拓展方式注入
 - p命名空间注入
 - c命名空间注入
### Bean的作用域
- 单例模式（Spring默认机制）
	```java
	<bean id="user2" class="com.kuang.pojo.User" c:name="小红" c:age="18" scope="singleton"/>
	```
- 原型模式
	每次从容器中get时，都产生一个新对象。
- request、session、application只能在web开发中使用。
## Bean的自动装配
- Spring会在上下文中自动寻找，自动给bean装配属性。
- Spring中有三种装配方式：
	- 在xml中显式配置
	- 在java中显式配置
	- 隐式的自动装配
### byName自动装配
- 会自动在容器上下文中，查找和自己对象set方法后的值对应的bean id。
### byType自动装配
- 会自动在容器上下文中，查找和自己对象的属性类型相同的bean。
- 使用byType时要保证class相同的bean唯一。
### 使用注解实现自动装配
- 使用注解前：
	- 导入context约束。
	- 配置注解的支持。
		```xml
		<context:annotation-config/>
		```
#### @Autowired
- 直接在需要自动装配的属性上使用，也可以在set方式上使用。
- 使用@Autowired时可以不用编写set方法，前提时这个需要自动装配的属性在IOC（Spring）容器中存在，且符合使用byName的要求。
- 如果显式定义Autowired中的required属性为false，说明这个对象可以为null，否则不允许为空。
- 如果自动装配无法通过@Autowired直接完成的时候，可以使用@Qualifier(value="xxx")去配置@Autowired的使用，指定一个唯一的bean对象注入。
#### @Autowired和@Resource
- 都用于自动装配，都可以放在属性字段上。
- @Autowired通过byType的方式实现。
- @Resource默认通过byName的方式实现，如果找不到对应的bean id，则通过byType的方式实现。
## 使用注解开发
### 注入属性
```java
@Component  
public class User {  
   public String name;  
   //相当于<property name="name" value="小红"/>
  @Value("小红")  
   public void setName(String name) {  
      this.name = name;  
  }  
}
```
### @Component的衍生注解
@Component有几个衍生注解。在web开发中，按照mvc三层架构分层。
- dao：@Repository
- service：@Service
- controller：@Controller
这四个注解的功能相同，都是将某个类注册到Spring中，装配Bean。
### 自动装配注解
- @Autowired：先通过类型，再通过名字自动装配。
- @Nullable：字段标记了这个注解，说明这个字段可以为null。
- @Resource：先通过名字，再通过类型自动装配。
### 作用域
#### 单例模式
```java
@Scope("singleton")
```
#### 原型模式
```java
@Scope("prototype")
```
## 使用Java的方式配置Spring
### 配置类
```java
//@Configuration代表这是一个配置类  
@Configuration  
@ComponentScan("com.kuang.pojo")  
@Import(MyConfig2.class)  
public class MyConfig {  
  //这个方法的名字相当于bean标签的id属性  
  //这个方法的返回值相当于bean标签的class属性  
  @Bean  
  public User getUser(){  
        return new User();  
  }  
}
```
### 实体类
```java
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.stereotype.Component;  
//这个注解说明这个类被注册到了容器中  
@Component  
public class User {  
    private String name;  
  
 public String getName() {  
        return name;  
  }  
@Value("小明")  
    public void setName(String name) {  
        this.name = name;  
  }  
  
    @Override  
  public String toString() {  
        return "User{" +  
                "name='" + name + '\'' +  
                '}';  
  }  
}
```
### 测试类
```java
public class MyTest {  
    @Test  
  public void test(){  
        //如果完全只使用配置类方式，就只能通过AnnotationConfig上下文来获取容器，通过配置类的class对象加载  
  ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);  
  User getUser = (User) context.getBean("getUser");  
  System.out.println(getUser.getName());  
  }  
}
```
## 代理模式
### 静态代理
- 抽象角色：一般使用接口或抽象类。
- 真实角色：被代理的角色。
- 代理角色：代理真实角色，在这之后进行一些附属操作。
- 客户：访问代理对象。
#### 静态代理缺点：一个真实角色对应一个代理角色，代码量大。
### 动态代理
- 动态代理的本质是反射机制的实现。
- 动态代理和静态代理含有的角色一样。
- 动态代理的代理类是动态生成的。
- 动态代理分为两大类：基于接口的动态代理、基于类的动态代理
	- 基于接口：JDK动态代理
	- 基于类：cglib
	- java字节码实现：javasist
#### InvocationHandler
- InvocationHandler是由代理实例的调用处理程序所实现的接口。
#### Proxy
- Proxy提供了创建动态代理类和实例的静态方法。
## AOP
- AOP意为面向切面编程，是通过预编译方式和运行期动态代理实现程序功能的统一维护的技术。
### AOP在Spring中的作用
- 提供声明式事务，允许用户自定义切面。
- 横切关注点：跨越应用程序多个模块的方法或功能，例如日志、安全、缓存、事务等。
- 切面（Aspect）：横切关注点被模块化的特殊对象。是一个类。
- 通知（Advice）：切面必须完成的工作。是类中的一个方法。
- 目标（Target）：被通知对象。
- 代理（Proxy）：向目标对象应用通知之后创建的对象。
- 切入点（PointCut）：切面通知执行的“地点”。
- 连接点（JointPoint）：与切入点匹配的执行点。
### 使用Spring实现AOP
- 方式1：使用Spring的API接口。
- 方式2：自定义类实现AOP（需要定义切面）。
- 方式3：使用注解实现。
## 整合Mybatis
### Mybatis-spring
1. 编写数据源配置
	```xml
	<!--DataSource:使用spring的数据源替换Mybatis的配置-->  
	 <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">  
		 <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>  
		 <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;userUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai"/>  
		 <property name="username" value="root"/>  
		 <property name="password" value="123456"/>  
	 </bean>
	```
2. sqlSessionFactory
	```xml
	<!--sqlSessionFactory-->  
	  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">  
		 <property name="dataSource" ref="dataSource"/>  
	<!-- 绑定Mybatis配置文件-->  
		 <property name="configLocation" value="classpath:mybatis_config.xml"/>  
		 <property name="mapperLocations" value="classpath:UserMapper.xml"/>  
	 </bean>
	```
3. sqlSessionTemplate
	```xml
	 <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">  
	<!-- 只能使用构造器注入sqlSessionFactory-->  
	  <constructor-arg index="0" ref="sqlSessionFactory"/>  
	 </bean>
	```
4. 给Mapper接口加实现类
	方式1：
	```java
	public class UserMapperImpl implements UserMapper{  
    private SqlSessionTemplate sqlSession;  
  
	 public void setSqlSession(SqlSessionTemplate sqlSession) {  
        this.sqlSession = sqlSession;  
	  }  
  
    @Override  
	  public List<User> selectUser() {  
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);  
	 return mapper.selectUser();  
	  }  
	}
	```
	方式2：
	```java
	public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper {  
    @Override  
	  public List<User> selectUser() {  
        SqlSession sqlSession = getSqlSession();  
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);  
		return mapper.selectUser();  
	  }  
	}
	```
5. 将写的实现类注入到Spring中
		方式1：
	```xml
	<bean id="userMapper" class="com.kuang.mapper.UserMapperImpl">  
	  <property name="sqlSession" ref="sqlSession"/>  
	</bean>
	```
	方式2：
	```xml
	<bean id="userMapper2" class="com.kuang.mapper.UserMapperImpl2">  
	 <property name="sqlSessionFactory" ref="sqlSessionFactory"/>  
	</bean>
	```
6. 测试
	```java
	//测试1
	@Test  
	  public void test() throws IOException {  
	  ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");  
	  UserMapper userMapper = context.getBean("userMapper", UserMapper.class);  
	  List<User> users = userMapper.selectUser();  
	  for (User user : users) {  
            System.out.println(user);  
	  }  
    }
	```
	## 声明式事务
	### 事务ACID原则：
	- 原子性
	- 一致性
	- 隔离性
	- 持久性
	### spring中 的事务管理
	- 声明式事务：AOP
		```java
		<!-- 结合AOP实现事务的织入-->  
		<!-- 配置事务通知-->  
		 <tx:advice id="txAdvice" transaction-manager="transactionManager">  
		<!-- 给哪些方法配置事务-->  
		<!-- 配置事务的传播特性:propagation-->  
		<tx:attributes>  
		 <tx:method name="add" propagation="REQUIRED"/>  
		 <tx:method name="delete" propagation="REQUIRED"/>  
		 <tx:method name="update" propagation="REQUIRED"/>  
		 <tx:method name="query" read-only="true"/>  
		 <tx:method name="*" propagation="REQUIRED"/>  
		</tx:attributes>  
		</tx:advice>  
		<!-- 配置事务切入-->  
	  <aop:config>  
		 <aop:pointcut id="txPointCut" expression="execution(* com.kuang.mapper.*.*(..))"/>  
		 <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>  
		</aop:config>
		```
	- 编程式事务：需要在代码中进行事务的管理
