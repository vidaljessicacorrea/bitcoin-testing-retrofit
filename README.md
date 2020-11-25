# REST API Testing

Technologies included in the tool:

- **Retrofit2:** http://square.github.io/retrofit
- **Gson:** https://github.com/google/gson
- **tinylog:** https://tinylog.org/

The following test allow to test

```
  As a bitcoin wallet owner
  I want to be able to transfer any amount of coins from my wallet account
  to any destination wallet account that I do not necessarily own
  So that to prove that I can securely purchase any service or goods by
  paying with BTC coins
  So that to prove that my transaction is secure and cannot be canceled or
  reverted by no one
```

## Configuration
Few configuration to set up Retrofit with some features:

- **Requests/responses logging:** Retrofit class `HttpLoggingInterceptor` is setup with `HttpLoggingInterceptor.Level.BASIC`
- **Responses converters:** there are two converters added: `ScalarsConverterFactory` and `GsonConverterFactory`

## Host Configuration
In order to make requests to the different endpoints of an API, we need to know the URI of the resource in question. 
Retrofit handles the part of the endpoint itself, so the host configuration of Aegis tries to achieve the centralization of the first part of the URI information: host, port and maybe a common base path to the endpoints.

The following is the schema for the JSON configuration you should follow when creating your file:

```
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "object",
  "properties": {
    "host-url": [
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "name": {
            "type": "string"
          },
          "host": {
            "type": "string"
          },
          "port": {
            "type": "integer"
          }
        },
        "required": [
          "name",
          "host"
        ]
      }
    ]
  },
  "required": [
    "host-url"
  ]
}
```

Example:

```
{
  "host-url": [
    {
      "name": "dc1-uat",
      "host": "http://my-host.com"
    }
  ]
}

```


### Starting from the scratch

The steps to start writing a test to hit an endpoint of this API are:

1. Add a configuration file to store the API environment information
2. Create a Java interface to represent the REST API and the different endpoints it supports
3. Add a POJO (DAO, Bean or whatever you want to call it) to deserialize the response
4. Create a test
5. Execute the test

#### Add a configuration file
For the first step, we have to create a JSON file with the host we want to use:

```
{
  "host-config": [
    {
      "name": "github",
      "host": "https://api.github.com"
    }
  ]
}
```

Save the file as config-sample.json in the resources folder of the project.
As you can see in the schema, the only required fields are name and host, that’s enough to hit the API.

#### Create a Java interface to represent the REST API
Retrofit will talk to the API using these interfaces. We are going to create a simple one to GET the */users* endpoint of com.github.GitHub:

```
public interface com.github.GitHub {

  @GET("/users/{user}")
  public Call<com.github.User> users(@Path("user") String user);
}
```

A few things to note here: `{user}` is defined as a replacement block and it will be replaced with the value of the method parameter user and the method will return a `Call` object of type `com.github.User` that will put Gson converter into action to deserialize the endpoint response.

#### Add a POJO
So we need the POJO for */user* response. This will look like:

```
public class com.github.User {

  private String login;
  private Integer id;
  private String name;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
```


#### Create a test
Now, we can start using all the above and create a first test:

```
@Test
public void verifyGitHubDefunktUser() throws IOException {
  com.github.GitHub gitHub = new EndpointFactory().getEndpoint(com.github.GitHub.class);
  Response<com.github.User> response = gitHub.users("defunkt").execute();
    
  Assert.assertEquals(response.code(), 200, "Response code should be 200.");
  Assert.assertTrue(response.body().getName().equals("Chris Wanstrath"), 
      "Defunkt name should be 'Chris Wanstrath'");
}
```

#### Execute the test
In order to execute the test against the host defined in the JSON file, you will need to specify its name through a system property named `aegis.host`. For our example, it will look like:

```
-Dhost=github
```

Then, the whole command for running the test will be:

```
mvn test -Dhost=github
```

However, if you only have one host configuration into the your file, like in *config-sample.json*, or if you want to use the first configuration, you could skip the system property and it will be selected by default.

So, `mvn test` will work as well.

## Setup Environment

We will configure and start a small local blockchain consisting of 2 interconnected bitcoin-core nodes
in our local environment. Once we start our nodes, we will be able to interact with their JSON-RPC API
via HTTP calls from our automated test project.
1. download the Bitcoin-core from https://bitcoin.org/en/download (Make sure to choose the
compressed file format: zip for windows or tar.gz for linux or macOS)

2. unzip / uncompress the content, you'll find a folder named bitcoin-0.X.X (i.e. bitcoin-0.20.1
)
3. Inside bitcoin-0.X.X/bin/ create an empty directory mydata1
4. Inside bitcoin-0.X.X/bin/ create an empty directory mydata2
5. Run inside bitcoin-0.X.X/bin/ the following commands in 2 separate consoles:
Console 1
start Node A:
```./bitcoin-qt -regtest -rpcuser=nodeA -rpcpassword=secretpassword1 -
rpcport=18443 -port=18444 -connect=127.0.0.1:28444 -datadir=./mydata1 -
printtoconsole -rpcallowip=0.0.0.0/0 -listen=1 -server -fallbackfee=0.0002
```
Console 2:
start Node B:
```
./bitcoin-qt -regtest -rpcuser=nodeB -rpcpassword=secretpassword2 -
rpcport=28443 -port=28444 -connect=127.0.0.1:18444 -datadir=./mydata2 -
printtoconsole -rpcallowip=0.0.0.0/0 -listen=1 -server -fallbackfee=0.0002
```