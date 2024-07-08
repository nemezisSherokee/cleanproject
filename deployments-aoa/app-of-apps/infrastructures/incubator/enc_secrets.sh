
helm secrets encrypt secrets.yaml.dec > secrets.yaml;
helm secrets encrypt secrets-dev.yaml.dec > secrets-dev.yaml;

#rm secrets.yaml.dec;
#rm secrets-dev.yaml.dec;
