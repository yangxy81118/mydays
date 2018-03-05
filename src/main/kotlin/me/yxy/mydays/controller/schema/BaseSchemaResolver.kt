package me.yxy.mydays.controller.schema

import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import java.io.File
import java.io.Reader
import javax.annotation.PostConstruct

abstract class BaseSchemaResolver {

    lateinit var graphQL : GraphQL

    @PostConstruct
    fun initGraphService() {
        var typeRegistry:TypeDefinitionRegistry = SchemaParser().parse(getResourceFile())
        var typeContent:RuntimeWiring =  buildWiringContent()
        val schema:GraphQLSchema = SchemaGenerator().makeExecutableSchema(typeRegistry,typeContent)
        graphQL = GraphQL.newGraphQL(schema).build()
    }

    /**
     * 获取graphql资源文件
     */
    abstract fun getResourceFile():Reader

    /**
     * 构建schema对应的fetcher
     */
    abstract fun buildWiringContent(): RuntimeWiring
}
