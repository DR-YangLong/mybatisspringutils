# mybatisspringutils
生成mybatis反向配置文件
## 使用说明
直接查看根目录下
>DBConnection.xml

修改相应的数据库连接属性，然后直接到tst目录下，修改
>AppTest.java

的
>public void testGeneratorUtil();

方法参数，然后直接运行，即可在根目录下生成逆向工程所需的配置文件。
## 方法说明
```java
g.init("com.transfar.greentech.model", "com.transfar.greentech.mapper", "com.transfar.greentech.dao", "shiro",
				"DBConnection.xml", "mysql", "bean", "dao");
```
参数含义：物理模型包，mapper接口包，dao接口包，项目名称，数据库连接位置，数据库类型，生成spring bean配置文件的pojo前缀，生成spring dao配置文件的dao前缀
## 生成结果示例：
generatorConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
	<classPathEntry location="请更换为你的jdbc驱动地址的绝对路径！" />
	<context id="context1">
		<jdbcConnection driverClass="com.mysql.jdbc.Driver"
			connectionURL="jdbc:mysql://127.0.0.1:3306/bpm" userId="root"
			password="root"><!--JDBC连接属性！ -->
		</jdbcConnection>
		<javaModelGenerator targetPackage="com.transfar.greentech.model"
			targetProject="shiro"><!--实体类位置！ -->
		</javaModelGenerator>
		<sqlMapGenerator targetPackage="com.transfar.greentech.mapper"
			targetProject="shiro"><!--映射文件位置！ -->
		</sqlMapGenerator>
		<javaClientGenerator targetPackage="com.transfar.greentech.dao"
			targetProject="shiro" type="XMLMAPPER"><!--DAO类位置！ -->
		</javaClientGenerator>
		<table tableName="work_platform" domainObjectName="WorkPlatform"
			enableInsert="true" enableSelectByPrimaryKey="true"
			enableUpdateByPrimaryKey="true" enableDeleteByPrimaryKey="true"
			enableSelectByExample="false" enableDeleteByExample="false"
			enableCountByExample="false" enableUpdateByExample="false" />
			</context>
</generatorConfiguration>
```
applicationContext-bean.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd"><!--以下是实体类配置 -->
	<bean id="bean_WorkPlatform" class="com.transfar.greentech.model.WorkPlatform"
		scope="prototype" />
</beans>
```
applicationContext-dao.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd"><!--以下是DAO类配置-->
    <bean id="dao_WorkPlatformMapper" class="org.mybatis.spring.mapper.MapperFactoryBean" scope="prototype">
        <property name="mapperInterface" value="com.transfar.greentech.dao.WorkPlatformMapper"/>
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
</beans>
```


