package com.toolkit.drools;

import java.util.Iterator;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DroolsEngine {

	private static Logger logger = LoggerFactory.getLogger(DroolsEngine.class);
	
	private KnowledgeBase knowledgeBase;

	public DroolsEngine(String[] drlFiles) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		for (int i = 0; i < drlFiles.length; i++) {
			kbuilder.add(ResourceFactory.newClassPathResource(drlFiles[i]), ResourceType.DRL);
		}

		if (kbuilder.hasErrors()) {
			KnowledgeBuilderErrors errors = kbuilder.getErrors();
			@SuppressWarnings("rawtypes")
			Iterator iterator = errors.iterator();
			while (iterator.hasNext()) {
				logger.error(iterator.next().toString());
			}
			throw new RuntimeException("compile error !");
		}

		this.knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		this.knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	}

	public void fireAllRules(Object object) {
		StatefulKnowledgeSession ksession = this.knowledgeBase.newStatefulKnowledgeSession();
		ksession.insert(object);
		ksession.fireAllRules();
		ksession.dispose();
	}
}