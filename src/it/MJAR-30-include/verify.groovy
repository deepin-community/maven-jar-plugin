
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.*;
import java.util.*;
import java.util.jar.*;
import org.codehaus.plexus.util.*;

boolean result = true;

try
{
    File target = new File( basedir, "target" );
    if ( !target.exists() || !target.isDirectory() )
    {
        System.err.println( "target file is missing or not a directory." );
        return false;
    }

    File artifact = new File( target, "maven-jar-plugin-test-mjar-30-02-99.0.jar" );
    if ( !artifact.exists() || artifact.isDirectory() )
    {
        System.err.println( "artifact file is missing or a directory." );
        return false;
    }

    String[] artifactNames = [ "service/TestInterface.class", "META-INF/MANIFEST.MF",
            "META-INF/maven/org.apache.maven.plugins/maven-jar-plugin-test-mjar-30-02/pom.properties",
            "META-INF/maven/org.apache.maven.plugins/maven-jar-plugin-test-mjar-30-02/pom.xml" ];

    Set contents = new HashSet();

    JarFile jar = new JarFile( artifact );
    Enumeration jarEntries = jar.entries();
    while ( jarEntries.hasMoreElements() )
    {
        JarEntry entry = (JarEntry) jarEntries.nextElement();
        if ( !entry.isDirectory() )
        {
            // Only compare files
            contents.add( entry.getName() );
        }
    }

    if  ( artifactNames.length != contents.size() )
    {
    	System.err.println( "jar content size is different from the expected content size" );
    	return false;
    }
    for ( int i = 0; i < artifactNames.length; i++ )
    {
        String artifactName = artifactNames[i];
		if ( !contents.contains( artifactName ) )
		{
        	System.err.println( "Artifact[" + artifactName + "] not found in jar archive" );
        	return false;
        }
    }

}
catch( Throwable e )
{
    e.printStackTrace();
    result = false;
}

return result;
