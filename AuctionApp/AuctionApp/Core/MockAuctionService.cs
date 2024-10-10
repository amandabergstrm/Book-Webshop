namespace AuctionApp.Core.Interfaces;

public class MockAuctionService: IAuctionService
{

    public List<Auction> GetAllByUserName(string userName)
    {
        return _auctions;
    }

    public Auction GetById(int id)
    {
        return _auctions.Find(a => a.AuctionId == id);
    }
    
    public void AddAuction(string name, string description, int startPrice, DateTime endDate, string userName)
    {
        throw new NotImplementedException("MockAuctionService.Add");
    }
    
    private static readonly List<Auction> _auctions = new();

    static MockAuctionService()
    {
        var auction1 = new Auction("Vintage Watch", "A rare vintage watch from the 1950s.", 1000, DateTime.Now.AddDays(5), "JohnDoe");
        var auction2 = new Auction("Antique Vase", "A beautiful antique vase from the 18th century.", 500, DateTime.Now.AddDays(3), "JaneSmith");

        // Create some bids for auction1
        var bid1 = new Bid(1, "Alice", 1100, DateTime.Now);
        var bid2 = new Bid(2, "Bob", 1200, DateTime.Now);

        // Create some bids for auction2
        var bid3 = new Bid(3, "Charlie", 550, DateTime.Now);
        var bid4 = new Bid(4, "Diana", 600, DateTime.Now);

        // Add bids to the auctions
        auction1.AddBid(bid1);
        auction1.AddBid(bid2);

        auction2.AddBid(bid3);
        auction2.AddBid(bid4);
    }
}