package com.marianowal.adminhouse.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.marianowal.adminhouse.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Grupo.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Grupo.class.getName() + ".calendarioComidas", jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Grupo.class.getName() + ".dias", jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Dia.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Dia.class.getName() + ".items", jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.ItemDia.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Comida.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Comida.class.getName() + ".ingredientes", jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Ingrediente.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Producto.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.CalendarioComida.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.PrecioProducto.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.UnidadMedida.class.getName(), jcacheConfiguration);
            cm.createCache(com.marianowal.adminhouse.domain.Grupo.class.getName() + ".users", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
