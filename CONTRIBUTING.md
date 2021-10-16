## 贡献
我们欢迎任何贡献！


但是仍然需要一些规则来约束。

### 我们使用cz来管理git提交信息
请先在本地安装nodejs/npm，之后初始化项目: `npm install`
```shell
# 首次使用需要初始化
$ npm install

# 提交使用
$ npm run commit

# 检查上一次提交的信息是否符合规范，使用
$ npm run lint-last-commit

# 检查一段字符串是否符合规范，使用
$ echo "commit message" | npx commitlint
```
每次一个小提交。

## 代码检查
使用`./gradlew check`进行代码检查。

原则上，只接受通过检查的带有单元测试的代码。
