import aiohttp
import asyncio


async def fetch(session, url):
    async with session.get(url) as response:
        return await response.text()


async def main():
    async with aiohttp.ClientSession() as session:
        tasks = []
        url = 'http://localhost:8080/MDSVP_war_exploded/Flood'
        for i in range(10000):
            tasks.append(fetch(session=session, url=url))
        htmls = await asyncio.gather(*tasks, return_exceptions=True)
        print(htmls)


if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
