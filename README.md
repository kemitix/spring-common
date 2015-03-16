# spring-common
Library of common Spring Components and associated classes used in my Spring Boot applications

[![Build Status](https://travis-ci.org/kemitix/spring-common.svg?branch=master)](https://travis-ci.org/kemitix/spring-common)

## Introduction

This Java library provides some Components for use with Spring Boot applications.

**Class**|&nbsp;
-----|-----
`ResourceReader` | For reading a file from a `Resource` into a String
`@Log`|Annotation class to enable injection of an appropriate `Logger`
`LoggerProvider`|Interface to provide a `Logger`
`@LogField`|Annotation class to indicate fields to be logged
`JdbcProperties` | Abstract class for holding JDBC `DataSource` properties
`JdbcDataSource` | `DataSource` wrapper for using a `JdbcProperties` instance
`Queryable` | Funtional interface for a querying by a String to get a `List`

## Usage

### ResourceReader

    package foo;

    import net.kemitix.spring.common.ResourceReader;

    @ComponentScan(
        {/* ... */,
            "net.kemitix.spring.common"})
    public class /* ... */ {

        /* ... */

        /**
         * The Resource we want to load.
         */
        @Bean
        @Qualifier("fooResource")
        public Resource fooResource() {
            return new ClassPathResource("foo.txt");
        }

        /**
         * Interface from this library.
         */
        @Autowired
        private ResourceReader resourceReader;

        /**
         * Use resourceReader to load the Resource as a String.
         */
        @Bean
        @Qualifier("foo")
        public String foo() throws IOException {
            return resourceReader.read(fooResource());
        }
    }

### @Log

    package foo;

    import net.kemitix.spring.common.logging.Log;

    @Component
    public class FooRunner {

        @Log
        private Logger logger;

        public void sayHelloLog() {
            logger.log(Level.INFO, "Hello, Log!");
        }
    }

### LoggerProvider & @LogField

    package foo;

    import net.kemitix.spring.common.logging.Log;
    import net.kemitix.spring.common.logging.LoggerProvider;
    import lombok.Setter;
    import lombok.Getter;

    @Component
    @ConfigurationProperties(prefix = "foo")
    @Setter
    @Getter
    public FooProperties implements LoggerProvider {

        @Log
        private Logger logger;

        /**
         * Log host, port and username
         */
        @LogField(name = "hostname")
        private String host;

        @LogField
        private int port;

        @LogField
        private String username;

        /**
         * Not logged as it is private
         */
        private String password;
    }

### JdbcProperties

    package foo;

    import net.kemitix.spring.common.JdbcProperties;
    import net.kemitix.spring.common.logging.Log;
    import lombok.Getter;

    @Component
    @ConfigurationProperties(prefix = "foo")
    @Getter
    public class FooJdbcProperties extends JdbcProperties {

        @Log
        private Logger logger;
    }

Because JdbcProperties implements LoggerProvider, it needs to provide an implementation of Logger getLogger(), which we do using Lombok's @Getter and spring-common's @Log annotations.

### JdbcDataSource

    package foo;

    @Component
    public class FooDataSource extends JdbcDataSource {

        @Autowired
        public FooDataSource(FooJdbcProperties properties) {
            super(properties);
        }
    }

Which can then be used to create a JdbcTemplate instance:

    package foo;

    @Component
    public class FooJdbcTemplate extends JdbcTemplate {

        @Autowired
        private FooJdbcTemplate(FooDataSource dataSource) {
            super(dataSource);
        }
    }

### Queryable

    package foo;

    import net.kemitix.spring.common.Queryable;

    @Component
    public class FooQuery implements Queryable<FooItem> {

        @Autowired
        private FooJdbcTemplate jdbcTemplate;

        /**
         * An SQL that expects a single placeholder parameter to be supplied.
         */
        @Autowired
        @Qualifier("fooSql")
        private String sql;

        @Autowired
        private FooItemRowMapper rowMapper;

        @Override
        public List<FooItem> query(String query) {
            return jdbcTemplate.query(sql, rowMapper, query);
        }
    }

## Contribution

Submit any Issues or Pull Requests via [GitHub](https://github.com/kemitix/spring-common).

### Setup a development environment
spring-resourcereader is built using Maven (at least version 3.0.4).
A simple import of the pom in your IDE should get you up and running.

### Requirements
The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## License
This project is released under The MIT License (MIT) (see LICENSE).
