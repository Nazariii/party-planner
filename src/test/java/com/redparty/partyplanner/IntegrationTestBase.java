package com.redparty.partyplanner;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testing")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
// todo consider moving to annotation?
public abstract class IntegrationTestBase {}
