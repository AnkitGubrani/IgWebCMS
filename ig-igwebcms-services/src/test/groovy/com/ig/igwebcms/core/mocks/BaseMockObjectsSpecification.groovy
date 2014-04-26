package com.ig.igwebcms.core.mocks

import groovy.transform.Synchronized
import org.apache.sling.commons.json.JSONObject
import org.apache.sling.commons.testing.jcr.MockNode
import org.apache.sling.commons.testing.jcr.MockNodeIterator
import org.apache.sling.commons.testing.jcr.MockProperty
import org.apache.sling.commons.testing.jcr.RepositoryUtil
import org.apache.sling.commons.testing.osgi.MockBundle
import org.apache.sling.commons.testing.osgi.MockBundleContext
import org.apache.sling.commons.testing.sling.MockResourceResolver
import org.apache.sling.commons.testing.sling.MockSlingHttpServletRequest
import org.apache.sling.commons.testing.sling.MockSlingHttpServletResponse
import org.apache.sling.jcr.api.SlingRepository
import spock.lang.Specification

import javax.jcr.Node
import javax.jcr.Session

class BaseMockObjectsSpecification extends Specification {

    static SlingRepository mockJcrRepository
    static Session mockJcrSession

    def setupSpec() {
        initializeRepository()
    }

    def cleanupSpec() {}

    def getMockList() {
        List mockList = new ArrayList<Object>()
        (1..6).each {
            mockList.add(new Object())
        }
        mockList
    }

    def getMockMap() {
        Map mockMap = new HashMap()
        (1..6).each { key ->
            mockMap.put(key, new Object())
        }
        mockMap
    }

    def getMockJsonObject() {
        JSONObject mockJsonObject = new JSONObject()
        mockJsonObject.put("MockJSON", "Mock data")
    }

    @Synchronized
    def initializeRepository() {
        if (!mockJcrRepository) {
            println "STARTING REPO"
            RepositoryUtil.startRepository()
            mockJcrRepository = RepositoryUtil.getRepository()
        }
        addShutdownHook {
            mockJcrSession.logout()
            RepositoryUtil.stopRepository()
        }
        mockJcrRepository
    }
    /**
     * @return JcrSession
     */
    def getJcrSession() {
        if (!mockJcrSession) {
            println "OPENING SESSION"
            mockJcrSession = mockJcrRepository.loginAdministrative(null)
        }
        mockJcrSession
    }
    /**
     *
     * @param jcrSession
     * @param nodeName
     * @param nodeType
     * @return addedNode
     */
    def addNode(Session jcrSession, String nodeName, String nodeType) {
        Node addedNode
        println "METHOD +" + jcrSession
        if (jcrSession) {
            addedNode = jcrSession.rootNode.addNode(nodeName, nodeType)
            println "added node =>" + addedNode
        }
        addedNode
    }
    /**
     *
     * @param resourceResolver
     * @param resourceType
     * @return customMockResource
     */
    def getMockResource(resourceResolver, resourceType) {
        CustomMockResource customMockResource = new CustomMockResource(resourceResolver, resourceType)
        customMockResource
    }

    def getMockResourceResolver() {
        MockResourceResolver mockResourceResolver = new MockResourceResolver()
        mockResourceResolver
    }

    def getMockBundle(long bundleId) {
        MockBundle mockBundle = new MockBundle(bundleId)
        mockBundle
    }

    def getMockBundleContext(MockBundle mockBundle) {
        MockBundleContext mockBundleContext = new MockBundleContext(mockBundle)
        mockBundleContext
    }

    def getMockNode(String path, String type) {
        MockNode mockNode = type ? new MockNode(path, type) : new MockNode(path)
        mockNode
    }

    def getMockNodeIterator(Node[] nodes) {
        MockNodeIterator mockNodeIterator = nodes ? new MockNodeIterator(nodes) : new MockNodeIterator()
        mockNodeIterator
    }

    def getMockSlingHttpServletRequest(String resourcePath, String selectors,
                                       String extension, String suffix, String queryString) {
        MockSlingHttpServletRequest mockSlingHttpServletRequest = new MockSlingHttpServletRequest(resourcePath, selectors, extension
                , suffix, queryString)
        mockSlingHttpServletRequest
    }

    def getMockSlingHttpServletResponse() {
        MockSlingHttpServletResponse mockSlingHttpServletResponse = Mock()
        mockSlingHttpServletResponse
    }

    def getMockProperty(String name) {
        MockProperty mockProperty = new MockProperty(name)
        mockProperty
    }
}
