# ecommerceTeste
Execução de Teste em E-Commerce

Framework de execução:
- IntelliJ Idea Community 2020.1
- Browser Chrome 83.0.4103.61 (64 bits)
- JDK 13
- Versões e outras informações no pom.xml

Antes de executar:

- Adicionar a seguinte informação no caminho Run - Edit Configurations - Cucumber Java - VM Options
```
-Dwebdriver.chrome.driver=src/test/drivers/chromedriver.exe -Dwebdriver=chrome
```