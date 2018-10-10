package com.roberttisma.web.protrig;

import com.roberttisma.web.protrig.service.PsqlDumpCommand;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProtrigApplicationTests {

	@Autowired
	private PsqlDumpCommand psqlDumpCommand;

	@Test
	public void testCommandGeneration() {
		val command = psqlDumpCommand.generateCommand();
		assertThat(command).isEqualTo("PGPASSWORD=password pg_dump -h localhost  -p 5432 -U postgres -t table1 -t table2 -t table3 myDbName");
	}

}
