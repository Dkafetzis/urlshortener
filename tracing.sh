./mvnw verify -DskipITs=false -Dquarkus.test.integration-test-profile=test-with-native-agent
./mvnw verify -Dnative -Dquarkus.native.agent-configuration-apply