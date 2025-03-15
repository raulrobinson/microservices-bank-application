.PHONY: run submit

run:
	{ \
		mvn -f accountEntity/pom.xml -Dtest=sampleTest.java test || true; \
		mvn -f client/pom.xml -Dtest=sampleTest.java test || true; \
		junit-merge -o xunitreport.xml accountEntity/target/surefire-reports/*sampleTest.xml client/target/surefire-reports/*sampleTest.xml; \
	}

submit:
	{ \
		mvn -f accountEntity/pom.xml -Dtest=mainTest.java test || true; \
		mvn -f client/pom.xml -Dtest=mainTest.java test || true; \
		junit-merge -o xunitreport.xml accountEntity/target/surefire-reports/*mainTest.xml client/target/surefire-reports/*mainTest.xml; \
	}
