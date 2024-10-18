# jpandas

A proof-of-concept Java project utilizing Python Pandas using GraalPy

## Samples

### Calculate Mean

Calculate the mean of an array of random numbers of size `size`:

```bash
curl -i localhost:8080/calculations/mean?size=20000
```

Sample response:

```json
{
  "result": 0.500045144812107,
  "duration": "PT38.571404632S"
}
```