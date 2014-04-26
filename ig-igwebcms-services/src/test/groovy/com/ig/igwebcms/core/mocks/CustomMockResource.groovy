package com.ig.igwebcms.core.mocks

import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.commons.testing.sling.MockResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CustomMockResource extends MockResource{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMockResource.class);

    private Map<String, List<Resource>> queryToresources = new HashMap<String, List<Resource>>();

    CustomMockResource(ResourceResolver resourceResolver, String path, String resourceType) {
        super(resourceResolver, path, resourceType)
    }

    public void addQueryToResources(String query, Resource... resources) {

        // oddness where passing in null gives an array of a single null element

        if (resources != null && resources.length == 1 && resources[0] == null) resources = null;

        List<Resource> resourceList = resources != null ? Arrays.asList(resources) : new ArrayList<Resource>();

        LOGGER.debug("Adding \"{}\" with {}", query, resourceList);

        queryToresources.put(query, resourceList);
    }

    /**

     * Simply returns the list of Resources passed into the constructor.

     * @param query    the query to lookup for the resources

     * @param language ignored

     * @return the Resources given in {@link #addQueryToResources(String, org.apache.sling.api.resource.Resource...)}

     * @see #addQueryToResources(String, org.apache.sling.api.resource.Resource...)

     */
    @Override
    public Iterator<Resource> findResources(String query, String language) {

        LOGGER.debug("Finding \"{}\"", query);

        List<Resource> resources = queryToresources.get(query);

        if (resources == null) throw new IllegalArgumentException("No query of \"" + query + "\" found");

        return resources.iterator();
    }
}
