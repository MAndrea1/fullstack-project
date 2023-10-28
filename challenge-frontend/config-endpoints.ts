const localConfig = {
  basetodosUrl: process.env.NEXT_PUBLIC_BASE_TODOS_LOCAL,
};

const remoteConfig = {
  basetodosUrl: process.env.NEXT_PUBLIC_BASE_TODOS_URL,
};

export const getEndpoint = () => {
  if (process.env.NEXT_PUBLIC_LOCAL_ENV === "true") {
    console.log("return local")
    return localConfig;
  } 
    console.log("return remote");
    return remoteConfig;
};

