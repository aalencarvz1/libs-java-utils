# Utilities Library

A lightweight utility library providing helpful reflection, JPA helper
functions, numeric/text utilities, and a Hibernate read‑only enforcement
listener.\
Designed to be used **without initializing Spring**, even though it
supports JPA/_Hibernate_ environments.

------------------------------------------------------------------------

## 📦 Maven Installation

Add the dependency to your `pom.xml`:

``` xml
<dependency>
    <groupId>io.github.aalencarvz1.libs.commons</groupId>
	<artifactId>utils</artifactId>
	<version>2.2.0</version>
</dependency>
```


------------------------------------------------------------------------

## 📂 Project Structure

This library contains the following core utility classes:

### **1. `JpaReflectionUtils`**

-   Resolve real entity type for `@JoinColumn`.
-   Resolve entity table names.
-   Detect generic fields.

### **2. `ReflectionUtils`**

-   Get all fields (including inherited).
-   Get all methods (including inherited).
-   Detect generic fields.

### **3. `NumberUtils`**

-   Safe numeric conversions.
-   String → `BigDecimal` tolerant parsing.

### **4. `TextUtils`**

-   String utilities (trim, digit filtering, equalsIgnoreCaseSafe).
-   Keep only last dot in strings.

### **5. `ObjectUtils`**

-   Coalescence util (`coalesce(a, b, c...)`).

### **6. `DefaultReadOnlyEventListener`**

A Hibernate event listener that **blocks INSERT/UPDATE/DELETE** at the
session level.

Register it manually:

``` java
DefaultReadOnlyEventListener.register(entityManagerFactory);
```

------------------------------------------------------------------------

## 🚀 Usage Examples

### Resolve referenced entity type

``` java
Field field = Campaign.class.getDeclaredField("kpi");
Optional<Class<?>> result =
        JpaReflectionUtils.resolveEntityType(field, Campaign.class);
```

### Safe BigDecimal conversion

``` java
BigDecimal value = NumberUtils.safeStringToBigDecimal("R$ 12,50");
```

### Coalesce

``` java
String result = ObjectUtils.coalesce(null, null, "value"); // returns "value"
```

### Enforce read‑only Hibernate session

``` java
@Configuration
public class DbConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean factory = builder.build();
        DefaultReadOnlyEventListener.register(factory.getObject());
        return factory;
    }
}
```

------------------------------------------------------------------------

## 🛠️ Cloning the Repository

``` bash
git clone https://github.com/yourcompany/jpa-utils.git
cd jpa-utils
```

------------------------------------------------------------------------

## 📜 License

MIT License --- free for personal and commercial use.

------------------------------------------------------------------------

## 👤 Author

**Alencar Velozo**\
Software Engineer --- JPA, Hibernate, Spring & Database Specialist

------------------------------------------------------------------------
